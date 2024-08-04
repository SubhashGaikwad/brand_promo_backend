import dayjs from 'dayjs/esm';
import { IOrganization } from 'app/entities/organization/organization.model';
import { ISecurityPermission } from 'app/entities/security-permission/security-permission.model';
import { ISecurityRole } from 'app/entities/security-role/security-role.model';
import { IDakMaster } from 'app/entities/dak-master/dak-master.model';

export interface ISecurityUser {
  id?: number;
  firstName?: string | null;
  lastName?: string | null;
  designation?: string | null;
  username?: string;
  passwordHash?: string;
  email?: string | null;
  description?: string | null;
  department?: string | null;
  imageUrl?: string | null;
  activated?: boolean | null;
  langKey?: string | null;
  activationKey?: string | null;
  resetKey?: string | null;
  resetDate?: dayjs.Dayjs | null;
  mobileNo?: string | null;
  createdBy?: string | null;
  createdOn?: dayjs.Dayjs | null;
  organization?: IOrganization | null;
  securityPermissions?: ISecurityPermission[] | null;
  securityRoles?: ISecurityRole[] | null;
  dakMasters?: IDakMaster[] | null;
}

export class SecurityUser implements ISecurityUser {
  constructor(
    public id?: number,
    public firstName?: string | null,
    public lastName?: string | null,
    public designation?: string | null,
    public username?: string,
    public passwordHash?: string,
    public email?: string | null,
    public description?: string | null,
    public department?: string | null,
    public imageUrl?: string | null,
    public activated?: boolean | null,
    public langKey?: string | null,
    public activationKey?: string | null,
    public resetKey?: string | null,
    public resetDate?: dayjs.Dayjs | null,
    public mobileNo?: string | null,
    public createdBy?: string | null,
    public createdOn?: dayjs.Dayjs | null,
    public organization?: IOrganization | null,
    public securityPermissions?: ISecurityPermission[] | null,
    public securityRoles?: ISecurityRole[] | null,
    public dakMasters?: IDakMaster[] | null
  ) {
    this.activated = this.activated ?? false;
  }
}

export function getSecurityUserIdentifier(securityUser: ISecurityUser): number | undefined {
  return securityUser.id;
}
