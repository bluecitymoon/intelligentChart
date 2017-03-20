(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('person-credit-card-activity', {
            parent: 'entity',
            url: '/person-credit-card-activity?page&sort&search&person_id={id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.personCreditCardActivity.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/person-credit-card-activity/person-credit-card-activities.html',
                    controller: 'PersonCreditCardActivityController',
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
                    $translatePartialLoader.addPart('personCreditCardActivity');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('person-credit-card-activity-detail', {
            parent: 'entity',
            url: '/person-credit-card-activity/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.personCreditCardActivity.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/person-credit-card-activity/person-credit-card-activity-detail.html',
                    controller: 'PersonCreditCardActivityDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('personCreditCardActivity');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PersonCreditCardActivity', function($stateParams, PersonCreditCardActivity) {
                    return PersonCreditCardActivity.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'person-credit-card-activity',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('person-credit-card-activity-detail.edit', {
            parent: 'person-credit-card-activity-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-credit-card-activity/person-credit-card-activity-dialog.html',
                    controller: 'PersonCreditCardActivityDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PersonCreditCardActivity', function(PersonCreditCardActivity) {
                            return PersonCreditCardActivity.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('person-credit-card-activity.new', {
            parent: 'person-credit-card-activity',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-credit-card-activity/person-credit-card-activity-dialog.html',
                    controller: 'PersonCreditCardActivityDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                amount: null,
                                createDate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('person-credit-card-activity', null, { reload: 'person-credit-card-activity' });
                }, function() {
                    $state.go('person-credit-card-activity');
                });
            }]
        })
        .state('person-credit-card-activity.edit', {
            parent: 'person-credit-card-activity',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-credit-card-activity/person-credit-card-activity-dialog.html',
                    controller: 'PersonCreditCardActivityDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PersonCreditCardActivity', function(PersonCreditCardActivity) {
                            return PersonCreditCardActivity.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('person-credit-card-activity', null, { reload: 'person-credit-card-activity' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('person-credit-card-activity.delete', {
            parent: 'person-credit-card-activity',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-credit-card-activity/person-credit-card-activity-delete-dialog.html',
                    controller: 'PersonCreditCardActivityDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PersonCreditCardActivity', function(PersonCreditCardActivity) {
                            return PersonCreditCardActivity.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('person-credit-card-activity', null, { reload: 'person-credit-card-activity' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
