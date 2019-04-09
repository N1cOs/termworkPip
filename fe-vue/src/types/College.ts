import AchievementScore from './AchievementScore';

export default interface College{
    id: number;
    city: string;
    name: string;
    places: number;
    logoUrl: string;
    abbreviation: string;
    description?: string;
    achievementsScore?: AchievementScore[];
}
