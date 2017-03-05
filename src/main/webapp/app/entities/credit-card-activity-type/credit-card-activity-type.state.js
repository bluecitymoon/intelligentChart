(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('credit-card-activity-type', {
            parent: 'entity',
            url: '/credit-card-activity-type?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.creditCardActivityType.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/credit-card-activity-type/credit-card-activity-types.html',
                    controller: 'CreditCardActivityTypeController',
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
                    $translatePartialLoader.addPart('creditCardActivityType');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('credit-card-activity-type-detail', {
            parent: 'entity',
            url: '/credit-card-activity-type/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.creditCardActivityType.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/credit-card-activity-type/credit-card-activity-type-detail.html',
                    controller: 'CreditCardActivityTypeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('creditCardActivityType');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CreditCardActivityType', function($stateParams, CreditCardActivityType) {
                    return CreditCardActivityType.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'credit-card-activity-type',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('credit-card-activity-type-detail.edit', {
            parent: 'credit-card-activity-type-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/credit-card-activity-type/credit-card-activity-type-dialog.html',
                    controller: 'CreditCardActivityTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CreditCardActivityType', function(CreditCardActivityType) {
                            return CreditCardActivityType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('credit-card-activity-type.new', {
            parent: 'credit-card-activity-type',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/credit-card-activity-type/credit-card-activity-type-dialog.html',
                    controller: 'CreditCardActivityTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('credit-card-activity-type', null, { reload: 'credit-card-activity-type' });
                }, function() {
                    $state.go('credit-card-activity-type');
                });
            }]
        })
        .state('credit-card-activity-type.edit', {
            parent: 'credit-card-activity-type',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/credit-card-activity-type/credit-card-activity-type-dialog.html',
                    controller: 'CreditCardActivityTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CreditCardActivityType', function(CreditCardActivityType) {
                            return CreditCardActivityType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('credit-card-activity-type', null, { reload: 'credit-card-activity-type' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('credit-card-activity-type.delete', {
            parent: 'credit-card-activity-type',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/credit-card-activity-type/credit-card-activity-type-delete-dialog.html',
                    controller: 'CreditCardActivityTypeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CreditCardActivityType', function(CreditCardActivityType) {
                            return CreditCardActivityType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('credit-card-activity-type', null, { reload: 'credit-card-activity-type' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
