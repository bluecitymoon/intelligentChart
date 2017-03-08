(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('person-taxi-activity', {
            parent: 'entity',
            url: '/person-taxi-activity?page&sort&search&person_id={id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.personTaxiActivity.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/person-taxi-activity/person-taxi-activities.html',
                    controller: 'PersonTaxiActivityController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('personTaxiActivity');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('person-taxi-activity-detail', {
            parent: 'entity',
            url: '/person-taxi-activity/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.personTaxiActivity.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/person-taxi-activity/person-taxi-activity-detail.html',
                    controller: 'PersonTaxiActivityDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('personTaxiActivity');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PersonTaxiActivity', function($stateParams, PersonTaxiActivity) {
                    return PersonTaxiActivity.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'person-taxi-activity',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('person-taxi-activity-detail.edit', {
            parent: 'person-taxi-activity-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-taxi-activity/person-taxi-activity-dialog.html',
                    controller: 'PersonTaxiActivityDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PersonTaxiActivity', function(PersonTaxiActivity) {
                            return PersonTaxiActivity.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('person-taxi-activity.new', {
            parent: 'person-taxi-activity',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-taxi-activity/person-taxi-activity-dialog.html',
                    controller: 'PersonTaxiActivityDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                startPlace: null,
                                destination: null,
                                createDate: null,
                                paidAmount: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('person-taxi-activity', null, { reload: 'person-taxi-activity' });
                }, function() {
                    $state.go('person-taxi-activity');
                });
            }]
        })
        .state('person-taxi-activity.edit', {
            parent: 'person-taxi-activity',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-taxi-activity/person-taxi-activity-dialog.html',
                    controller: 'PersonTaxiActivityDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PersonTaxiActivity', function(PersonTaxiActivity) {
                            return PersonTaxiActivity.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('person-taxi-activity', null, { reload: 'person-taxi-activity' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('person-taxi-activity.delete', {
            parent: 'person-taxi-activity',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-taxi-activity/person-taxi-activity-delete-dialog.html',
                    controller: 'PersonTaxiActivityDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PersonTaxiActivity', function(PersonTaxiActivity) {
                            return PersonTaxiActivity.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('person-taxi-activity', null, { reload: 'person-taxi-activity' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
