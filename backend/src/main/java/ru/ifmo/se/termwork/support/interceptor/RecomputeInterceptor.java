package ru.ifmo.se.termwork.support.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import ru.ifmo.se.termwork.domain.Student;
import ru.ifmo.se.termwork.domain.User;
import ru.ifmo.se.termwork.repository.StudentRepository;
import ru.ifmo.se.termwork.security.Role;
import ru.ifmo.se.termwork.service.ComputeService;
import ru.ifmo.se.termwork.support.annotation.RecomputeScore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RecomputeInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ComputeService computeService;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {
        if(handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            RecomputeScore methodAnnotation = handlerMethod.getMethodAnnotation(RecomputeScore.class);
            RecomputeScore classAnnotation = handlerMethod.getBeanType().getAnnotation(RecomputeScore.class);
            RecomputeScore recomputeScore = methodAnnotation == null ? classAnnotation : methodAnnotation;
            if(recomputeScore != null){
                User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                if(user.hasRole(Role.STUDENT)){
                    Student student;
                    switch (recomputeScore.value()){
                        case EXAMS:
                            student = computeService.recomputeExams(user.getId());
                            break;
                        case ACHIEVEMENTS:
                            student = computeService.recomputeAchievements(user.getId());
                            break;
                        default:
                            student = computeService.recomputeAll(user.getId());
                            break;
                    }
                    studentRepository.save(student);
                }
            }
        }
    }
}
