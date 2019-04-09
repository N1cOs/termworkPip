import Exam from './Exam';
import Achievement from './Achievement';
import UserRating from './UserRating';

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
    ratings?: UserRating[];
}
