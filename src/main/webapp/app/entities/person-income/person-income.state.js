(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('person-income', {
            parent: 'entity',
            url: '/person-income?page&sort&search&person_id={id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.personIncome.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/person-income/person-incomes.html',
                    controller: 'PersonIncomeController',
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
                    $translatePartialLoader.addPart('personIncome');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('person-income-detail', {
            parent: 'entity',
            url: '/person-income/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.personIncome.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/person-income/person-income-detail.html',
                    controller: 'PersonIncomeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('personIncome');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PersonIncome', function($stateParams, PersonIncome) {
                    return PersonIncome.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'person-income',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('person-income-detail.edit', {
            parent: 'person-income-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-income/person-income-dialog.html',
                    controller: 'PersonIncomeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PersonIncome', function(PersonIncome) {
                            return PersonIncome.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('person-income.new', {
            parent: 'person-income',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-income/person-income-dialog.html',
                    controller: 'PersonIncomeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                year: null,
                                inCountrySalaryTotal: null,
                                inCountryPlusBoxTotal: null,
                                outCountrySalaryTotal: null,
                                outCountryPlusBoxTotal: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('person-income', null, { reload: 'person-income' });
                }, function() {
                    $state.go('person-income');
                });
            }]
        })
        .state('person-income.edit', {
            parent: 'person-income',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-income/person-income-dialog.html',
                    controller: 'PersonIncomeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PersonIncome', function(PersonIncome) {
                            return PersonIncome.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('person-income', null, { reload: 'person-income' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('person-income.delete', {
            parent: 'person-income',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-income/person-income-delete-dialog.html',
                    controller: 'PersonIncomeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PersonIncome', function(PersonIncome) {
                            return PersonIncome.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('person-income', null, { reload: 'person-income' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
