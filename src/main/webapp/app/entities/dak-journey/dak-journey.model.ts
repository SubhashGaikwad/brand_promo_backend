import dayjs from 'dayjs/esm';
import { IDakMaster } from 'app/entities/dak-master/dak-master.model';
import { ISecurityUser } from 'app/entities/security-user/security-user.model';
import { ICommentMaster } from 'app/entities/comment-master/comment-master.model';
import { DakStatus } from 'app/entities/enumerations/dak-status.model';

export interface IDakJourney {
  id?: number;
  assignedOn?: dayjs.Dayjs | null;
  updatedOn?: dayjs.Dayjs | null;
  dakStatus?: DakStatus | null;
  status?: boolean | null;
  dakAssignedBy?: string | null;
  dakAssignedTo?: string | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  dakMaster?: IDakMaster | null;
  securityUser?: ISecurityUser | null;
  commentMaster?: ICommentMaster | null;
}

export class DakJourney implements IDakJourney {
  constructor(
    public id?: number,
    public assignedOn?: dayjs.Dayjs | null,
    public updatedOn?: dayjs.Dayjs | null,
    public dakStatus?: DakStatus | null,
    public status?: boolean | null,
    public dakAssignedBy?: string | null,
    public dakAssignedTo?: string | null,
    public lastModified?: dayjs.Dayjs | null,
    public lastModifiedBy?: string | null,
    public dakMaster?: IDakMaster | null,
    public securityUser?: ISecurityUser | null,
    public commentMaster?: ICommentMaster | null
  ) {
    this.status = this.status ?? false;
  }
}

export function getDakJourneyIdentifier(dakJourney: IDakJourney): number | undefined {
  return dakJourney.id;
}
