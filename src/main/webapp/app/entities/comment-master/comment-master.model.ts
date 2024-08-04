import dayjs from 'dayjs/esm';
import { ISecurityUser } from 'app/entities/security-user/security-user.model';
import { IDakMaster } from 'app/entities/dak-master/dak-master.model';

export interface ICommentMaster {
  id?: number;
  description?: string | null;
  createdOn?: dayjs.Dayjs | null;
  createdBy?: string | null;
  status?: boolean | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  securityUser?: ISecurityUser | null;
  dakMaster?: IDakMaster | null;
}

export class CommentMaster implements ICommentMaster {
  constructor(
    public id?: number,
    public description?: string | null,
    public createdOn?: dayjs.Dayjs | null,
    public createdBy?: string | null,
    public status?: boolean | null,
    public lastModified?: dayjs.Dayjs | null,
    public lastModifiedBy?: string | null,
    public securityUser?: ISecurityUser | null,
    public dakMaster?: IDakMaster | null
  ) {
    this.status = this.status ?? false;
  }
}

export function getCommentMasterIdentifier(commentMaster: ICommentMaster): number | undefined {
  return commentMaster.id;
}
