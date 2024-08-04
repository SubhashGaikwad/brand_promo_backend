import dayjs from 'dayjs/esm';
import { ISecurityPermission } from 'app/entities/security-permission/security-permission.model';
import { ISecurityUser } from 'app/entities/security-user/security-user.model';

export interface ISecurityRole {
  id?: number;
  roleName?: string;
  description?: string | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  securityPermissions?: ISecurityPermission[] | null;
  securityUsers?: ISecurityUser[] | null;
}

export class SecurityRole implements ISecurityRole {
  constructor(
    public id?: number,
    public roleName?: string,
    public description?: string | null,
    public lastModified?: dayjs.Dayjs | null,
    public lastModifiedBy?: string | null,
    public securityPermissions?: ISecurityPermission[] | null,
    public securityUsers?: ISecurityUser[] | null
  ) {}
}

export function getSecurityRoleIdentifier(securityRole: ISecurityRole): number | undefined {
  return securityRole.id;
}
