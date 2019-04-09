import Requirement from './Requirement';
import College from './College';

export default interface Speciality {
  id: number;
  name: string;
  okso: string;
  places: number;
  requirements?: Requirement[];
  college?: College;
}
