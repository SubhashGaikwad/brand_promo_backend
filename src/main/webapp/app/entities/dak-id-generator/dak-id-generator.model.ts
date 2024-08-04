import { IOrganization } from 'app/entities/organization/organization.model';

export interface IDakIdGenerator {
  id?: number;
  nextValInward?: number | null;
  nextValOutward?: number | null;
  organization?: IOrganization | null;
}

export class DakIdGenerator implements IDakIdGenerator {
  constructor(
    public id?: number,
    public nextValInward?: number | null,
    public nextValOutward?: number | null,
    public organization?: IOrganization | null
  ) {}
}

export function getDakIdGeneratorIdentifier(dakIdGenerator: IDakIdGenerator): number | undefined {
  return dakIdGenerator.id;
}
