import Subject from './Subject';

export default interface Requirement{
    subject: Subject;
    minScore: number;
    minLevelOfOlympiad: number;
}