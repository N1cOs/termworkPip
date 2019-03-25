import Subject from "@/types/Subject";

export default interface RatingRecord {
  userFullName: string;
  // pontsSum: number;
  subjects: Subject[];
  // place: number;
  // placeWithOriginals: number;
}
