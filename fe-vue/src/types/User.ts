import Exam from './Exam';
import Achievement from './Achievement';

export default interface User {
    surname: string;
    name: string;
    patronymic?: string;
    birthDate?: string;
    serialNumber: string;
    email: string;
    phone?: string;
    password: string;
    exams?: Exam[];
    achievements?: Achievement[];
}
