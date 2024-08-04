import dayjs from 'dayjs/esm';
import { ISecurityUser } from 'app/entities/security-user/security-user.model';
import { RegisterType } from 'app/entities/enumerations/register-type.model';

export interface IGhoshwara {
  id?: number;
  registerType?: RegisterType | null;
  initialPendings?: number | null;
  currentWeekInwards?: number | null;
  total?: number | null;
  selfGenerated?: number | null;
  currentWeekCleared?: number | null;
  weeklyPendings?: number | null;
  firstWeek?: number | null;
  secondWeek?: number | null;
  thirdWeek?: number | null;
  firstMonth?: number | null;
  secondMonth?: number | null;
  thirdMonth?: number | null;
  withinSixMonths?: number | null;
  date?: dayjs.Dayjs | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  securityUser?: ISecurityUser | null;
}

export class Ghoshwara implements IGhoshwara {
  constructor(
    public id?: number,
    public registerType?: RegisterType | null,
    public initialPendings?: number | null,
    public currentWeekInwards?: number | null,
    public total?: number | null,
    public selfGenerated?: number | null,
    public currentWeekCleared?: number | null,
    public weeklyPendings?: number | null,
    public firstWeek?: number | null,
    public secondWeek?: number | null,
    public thirdWeek?: number | null,
    public firstMonth?: number | null,
    public secondMonth?: number | null,
    public thirdMonth?: number | null,
    public withinSixMonths?: number | null,
    public date?: dayjs.Dayjs | null,
    public lastModified?: dayjs.Dayjs | null,
    public lastModifiedBy?: string | null,
    public securityUser?: ISecurityUser | null
  ) {}
}

export function getGhoshwaraIdentifier(ghoshwara: IGhoshwara): number | undefined {
  return ghoshwara.id;
}
