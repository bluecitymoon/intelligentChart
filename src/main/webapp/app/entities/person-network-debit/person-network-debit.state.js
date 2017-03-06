(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('person-network-debit', {
            parent: 'entity',
            url: '/person-network-debit?page&sort&search&person_id={id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.personNetworkDebit.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/person-network-debit/person-network-debits.html',
                    controller: 'PersonNetworkDebitController',
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
                    $translatePartialLoader.addPart('personNetworkDebit');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('person-network-debit-detail', {
            parent: 'entity',
            url: '/person-network-debit/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.personNetworkDebit.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/person-network-debit/person-network-debit-detail.html',
                    controller: 'PersonNetworkDebitDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('personNetworkDebit');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PersonNetworkDebit', function($stateParams, PersonNetworkDebit) {
                    return PersonNetworkDebit.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'person-network-debit',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('person-network-debit-detail.edit', {
            parent: 'person-network-debit-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-network-debit/person-network-debit-dialog.html',
                    controller: 'PersonNetworkDebitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PersonNetworkDebit', function(PersonNetworkDebit) {
                            return PersonNetworkDebit.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('person-network-debit.new', {
            parent: 'person-network-debit',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-network-debit/person-network-debit-dialog.html',
                    controller: 'PersonNetworkDebitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                createDate: null,
                                amount: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('person-network-debit', null, { reload: 'person-network-debit' });
                }, function() {
                    $state.go('person-network-debit');
                });
            }]
        })
        .state('person-network-debit.edit', {
            parent: 'person-network-debit',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-network-debit/person-network-debit-dialog.html',
                    controller: 'PersonNetworkDebitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PersonNetworkDebit', function(PersonNetworkDebit) {
                            return PersonNetworkDebit.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('person-network-debit', null, { reload: 'person-network-debit' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('person-network-debit.delete', {
            parent: 'person-network-debit',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-network-debit/person-network-debit-delete-dialog.html',
                    controller: 'PersonNetworkDebitDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PersonNetworkDebit', function(PersonNetworkDebit) {
                            return PersonNetworkDebit.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('person-network-debit', null, { reload: 'person-network-debit' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
