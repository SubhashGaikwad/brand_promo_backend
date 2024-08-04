import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'security-user',
        data: { pageTitle: 'eofficeApp.securityUser.home.title' },
        loadChildren: () => import('./security-user/security-user.module').then(m => m.SecurityUserModule),
      },
      {
        path: 'user-access',
        data: { pageTitle: 'eofficeApp.userAccess.home.title' },
        loadChildren: () => import('./user-access/user-access.module').then(m => m.UserAccessModule),
      },
      {
        path: 'security-role',
        data: { pageTitle: 'eofficeApp.securityRole.home.title' },
        loadChildren: () => import('./security-role/security-role.module').then(m => m.SecurityRoleModule),
      },
      {
        path: 'security-permission',
        data: { pageTitle: 'eofficeApp.securityPermission.home.title' },
        loadChildren: () => import('./security-permission/security-permission.module').then(m => m.SecurityPermissionModule),
      },
      {
        path: 'organization',
        data: { pageTitle: 'eofficeApp.organization.home.title' },
        loadChildren: () => import('./organization/organization.module').then(m => m.OrganizationModule),
      },
      {
        path: 'dak-master',
        data: { pageTitle: 'eofficeApp.dakMaster.home.title' },
        loadChildren: () => import('./dak-master/dak-master.module').then(m => m.DakMasterModule),
      },
      {
        path: 'dak-id-generator',
        data: { pageTitle: 'eofficeApp.dakIdGenerator.home.title' },
        loadChildren: () => import('./dak-id-generator/dak-id-generator.module').then(m => m.DakIdGeneratorModule),
      },
      {
        path: 'parameter-lookup',
        data: { pageTitle: 'eofficeApp.parameterLookup.home.title' },
        loadChildren: () => import('./parameter-lookup/parameter-lookup.module').then(m => m.ParameterLookupModule),
      },
      {
        path: 'comment-master',
        data: { pageTitle: 'eofficeApp.commentMaster.home.title' },
        loadChildren: () => import('./comment-master/comment-master.module').then(m => m.CommentMasterModule),
      },
      {
        path: 'hearing-details',
        data: { pageTitle: 'eofficeApp.hearingDetails.home.title' },
        loadChildren: () => import('./hearing-details/hearing-details.module').then(m => m.HearingDetailsModule),
      },
      {
        path: 'dak-history',
        data: { pageTitle: 'eofficeApp.dakHistory.home.title' },
        loadChildren: () => import('./dak-history/dak-history.module').then(m => m.DakHistoryModule),
      },
      {
        path: 'dak-journey',
        data: { pageTitle: 'eofficeApp.dakJourney.home.title' },
        loadChildren: () => import('./dak-journey/dak-journey.module').then(m => m.DakJourneyModule),
      },
      {
        path: 'ghoshwara',
        data: { pageTitle: 'eofficeApp.ghoshwara.home.title' },
        loadChildren: () => import('./ghoshwara/ghoshwara.module').then(m => m.GhoshwaraModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
