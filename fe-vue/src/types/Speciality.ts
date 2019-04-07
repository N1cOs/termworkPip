import Requirement from './Requirement';

export default interface Speciality {
  id: number;
  name: string;
  okso: string;
  places: number;
  requirements?: Requirement[];
}
