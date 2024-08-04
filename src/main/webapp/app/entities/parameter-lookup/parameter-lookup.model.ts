import dayjs from 'dayjs/esm';
import { IOrganization } from 'app/entities/organization/organization.model';

export interface IParameterLookup {
  id?: number;
  parameterName?: string;
  parameterValue?: string;
  type?: string;
  status?: string | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  createdBy?: string | null;
  createdOn?: dayjs.Dayjs | null;
  organization?: IOrganization | null;
}

export class ParameterLookup implements IParameterLookup {
  constructor(
    public id?: number,
    public parameterName?: string,
    public parameterValue?: string,
    public type?: string,
    public status?: string | null,
    public lastModified?: dayjs.Dayjs | null,
    public lastModifiedBy?: string | null,
    public createdBy?: string | null,
    public createdOn?: dayjs.Dayjs | null,
    public organization?: IOrganization | null
  ) {}
}

export function getParameterLookupIdentifier(parameterLookup: IParameterLookup): number | undefined {
  return parameterLookup.id;
}
