import dayjs from 'dayjs/esm';
import { IOrganization } from 'app/entities/organization/organization.model';
import { ISecurityUser } from 'app/entities/security-user/security-user.model';
import { DakStatus } from 'app/entities/enumerations/dak-status.model';
import { LetterType } from 'app/entities/enumerations/letter-type.model';

export interface IDakMaster {
  id?: number;
  inwardNumber?: string | null;
  senderName?: string | null;
  contactNumber?: string | null;
  senderAddress?: string | null;
  senderEmail?: string | null;
  subject?: string | null;
  letterDate?: dayjs.Dayjs | null;
  currentStatus?: DakStatus | null;
  letterStatus?: boolean | null;
  letterReceivedDate?: dayjs.Dayjs | null;
  awaitReason?: string | null;
  dispatchDate?: dayjs.Dayjs | null;
  createdBy?: string | null;
  letterType?: LetterType | null;
  isResponseReceived?: boolean | null;
  assignedDate?: dayjs.Dayjs | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  dakAssignedFrom?: string | null;
  dakAssignee?: string | null;
  dispatchBy?: string | null;
  senderOutward?: string | null;
  outwardNumber?: string | null;
  taluka?: string | null;
  organization?: IOrganization | null;
  securityUsers?: ISecurityUser[] | null;
}

export class DakMaster implements IDakMaster {
  constructor(
    public id?: number,
    public inwardNumber?: string | null,
    public senderName?: string | null,
    public contactNumber?: string | null,
    public senderAddress?: string | null,
    public senderEmail?: string | null,
    public subject?: string | null,
    public letterDate?: dayjs.Dayjs | null,
    public currentStatus?: DakStatus | null,
    public letterStatus?: boolean | null,
    public letterReceivedDate?: dayjs.Dayjs | null,
    public awaitReason?: string | null,
    public dispatchDate?: dayjs.Dayjs | null,
    public createdBy?: string | null,
    public letterType?: LetterType | null,
    public isResponseReceived?: boolean | null,
    public assignedDate?: dayjs.Dayjs | null,
    public lastModified?: dayjs.Dayjs | null,
    public lastModifiedBy?: string | null,
    public dakAssignedFrom?: string | null,
    public dakAssignee?: string | null,
    public dispatchBy?: string | null,
    public senderOutward?: string | null,
    public outwardNumber?: string | null,
    public taluka?: string | null,
    public organization?: IOrganization | null,
    public securityUsers?: ISecurityUser[] | null
  ) {
    this.letterStatus = this.letterStatus ?? false;
    this.isResponseReceived = this.isResponseReceived ?? false;
  }
}

export function getDakMasterIdentifier(dakMaster: IDakMaster): number | undefined {
  return dakMaster.id;
}
