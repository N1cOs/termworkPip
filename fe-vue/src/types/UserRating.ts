import Speciality from './Speciality';

export default interface UserRating{
    totalScore: number;
    originals: boolean;
    place: number;
    placeOriginal: number;
    olympiad: boolean;
    speciality: Speciality;
}