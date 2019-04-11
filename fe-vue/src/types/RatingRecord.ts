export default interface RatingRecord {
  bvi: []
  ege: {
    originals: boolean
    place: number
    placeOriginal: number
    student: {
      name: string
      surname: string
      patronymic: string
      exams: {}[]
    }
    submissionDate: string
    success: boolean
    totalScore: number
  }[]
}
