import dayjs from 'dayjs/esm';
import { ISecurityRole } from 'app/entities/security-role/security-role.model';
import { ISecurityUser } from 'app/entities/security-user/security-user.model';

export interface ISecurityPermission {
  id?: number;
  permissionName?: string;
  description?: string | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  securityRoles?: ISecurityRole[] | null;
  securityUsers?: ISecurityUser[] | null;
}

export class SecurityPermission implements ISecurityPermission {
  constructor(
    public id?: number,
    public permissionName?: string,
    public description?: string | null,
    public lastModified?: dayjs.Dayjs | null,
    public lastModifiedBy?: string | null,
    public securityRoles?: ISecurityRole[] | null,
    public securityUsers?: ISecurityUser[] | null
  ) {}
}

export function getSecurityPermissionIdentifier(securityPermission: ISecurityPermission): number | undefined {
  return securityPermission.id;
}
