import Exam from "@/types/Exam";

export default interface RatingRecord {
  userFullName: string;
  // pontsSum: number;
  subjects: Exam[];
  // place: number;
  // placeWithOriginals: number;
}
