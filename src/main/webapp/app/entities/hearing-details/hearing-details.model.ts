import dayjs from 'dayjs/esm';
import { IDakMaster } from 'app/entities/dak-master/dak-master.model';
import { DakStatus } from 'app/entities/enumerations/dak-status.model';

export interface IHearingDetails {
  id?: number;
  accuserName?: string | null;
  orderDate?: dayjs.Dayjs | null;
  respondentName?: string | null;
  comment?: string | null;
  date?: dayjs.Dayjs | null;
  time?: dayjs.Dayjs | null;
  status?: DakStatus | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  dakMaster?: IDakMaster | null;
}

export class HearingDetails implements IHearingDetails {
  constructor(
    public id?: number,
    public accuserName?: string | null,
    public orderDate?: dayjs.Dayjs | null,
    public respondentName?: string | null,
    public comment?: string | null,
    public date?: dayjs.Dayjs | null,
    public time?: dayjs.Dayjs | null,
    public status?: DakStatus | null,
    public lastModified?: dayjs.Dayjs | null,
    public lastModifiedBy?: string | null,
    public dakMaster?: IDakMaster | null
  ) {}
}

export function getHearingDetailsIdentifier(hearingDetails: IHearingDetails): number | undefined {
  return hearingDetails.id;
}
