export default interface Speciality {
  id: number,
  name: string,
  subjects?: { subjectName: string, minimalPoints: number }[]

}
