import dayjs from 'dayjs/esm';
import { IDakMaster } from 'app/entities/dak-master/dak-master.model';
import { ISecurityUser } from 'app/entities/security-user/security-user.model';
import { DakStatus } from 'app/entities/enumerations/dak-status.model';

export interface IDakHistory {
  id?: number;
  date?: dayjs.Dayjs | null;
  assignedBy?: string | null;
  assignedOn?: dayjs.Dayjs | null;
  createdOn?: dayjs.Dayjs | null;
  dakStatus?: DakStatus | null;
  status?: boolean | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  dakMaster?: IDakMaster | null;
  securityUser?: ISecurityUser | null;
}

export class DakHistory implements IDakHistory {
  constructor(
    public id?: number,
    public date?: dayjs.Dayjs | null,
    public assignedBy?: string | null,
    public assignedOn?: dayjs.Dayjs | null,
    public createdOn?: dayjs.Dayjs | null,
    public dakStatus?: DakStatus | null,
    public status?: boolean | null,
    public lastModified?: dayjs.Dayjs | null,
    public lastModifiedBy?: string | null,
    public dakMaster?: IDakMaster | null,
    public securityUser?: ISecurityUser | null
  ) {
    this.status = this.status ?? false;
  }
}

export function getDakHistoryIdentifier(dakHistory: IDakHistory): number | undefined {
  return dakHistory.id;
}
