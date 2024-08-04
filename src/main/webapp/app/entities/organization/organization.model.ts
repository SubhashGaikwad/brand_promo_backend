import dayjs from 'dayjs/esm';
import { OrganizationType } from 'app/entities/enumerations/organization-type.model';

export interface IOrganization {
  id?: number;
  organizationName?: string;
  address?: string | null;
  createdOn?: string | null;
  description?: string | null;
  isActivate?: boolean | null;
  orgnizationType?: OrganizationType;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
}

export class Organization implements IOrganization {
  constructor(
    public id?: number,
    public organizationName?: string,
    public address?: string | null,
    public createdOn?: string | null,
    public description?: string | null,
    public isActivate?: boolean | null,
    public orgnizationType?: OrganizationType,
    public lastModified?: dayjs.Dayjs | null,
    public lastModifiedBy?: string | null
  ) {
    this.isActivate = this.isActivate ?? false;
  }
}

export function getOrganizationIdentifier(organization: IOrganization): number | undefined {
  return organization.id;
}
