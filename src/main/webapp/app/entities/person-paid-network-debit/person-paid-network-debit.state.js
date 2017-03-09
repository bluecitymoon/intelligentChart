(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('person-paid-network-debit', {
            parent: 'entity',
            url: '/person-paid-network-debit?page&sort&search&person_id={id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.personPaidNetworkDebit.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/person-paid-network-debit/person-paid-network-debits.html',
                    controller: 'PersonPaidNetworkDebitController',
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
                    $translatePartialLoader.addPart('personPaidNetworkDebit');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('person-paid-network-debit-detail', {
            parent: 'entity',
            url: '/person-paid-network-debit/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.personPaidNetworkDebit.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/person-paid-network-debit/person-paid-network-debit-detail.html',
                    controller: 'PersonPaidNetworkDebitDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('personPaidNetworkDebit');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PersonPaidNetworkDebit', function($stateParams, PersonPaidNetworkDebit) {
                    return PersonPaidNetworkDebit.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'person-paid-network-debit',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('person-paid-network-debit-detail.edit', {
            parent: 'person-paid-network-debit-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-paid-network-debit/person-paid-network-debit-dialog.html',
                    controller: 'PersonPaidNetworkDebitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PersonPaidNetworkDebit', function(PersonPaidNetworkDebit) {
                            return PersonPaidNetworkDebit.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('person-paid-network-debit.new', {
            parent: 'person-paid-network-debit',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-paid-network-debit/person-paid-network-debit-dialog.html',
                    controller: 'PersonPaidNetworkDebitDialogController',
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
                    $state.go('person-paid-network-debit', null, { reload: 'person-paid-network-debit' });
                }, function() {
                    $state.go('person-paid-network-debit');
                });
            }]
        })
        .state('person-paid-network-debit.edit', {
            parent: 'person-paid-network-debit',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-paid-network-debit/person-paid-network-debit-dialog.html',
                    controller: 'PersonPaidNetworkDebitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PersonPaidNetworkDebit', function(PersonPaidNetworkDebit) {
                            return PersonPaidNetworkDebit.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('person-paid-network-debit', null, { reload: 'person-paid-network-debit' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('person-paid-network-debit.delete', {
            parent: 'person-paid-network-debit',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-paid-network-debit/person-paid-network-debit-delete-dialog.html',
                    controller: 'PersonPaidNetworkDebitDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PersonPaidNetworkDebit', function(PersonPaidNetworkDebit) {
                            return PersonPaidNetworkDebit.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('person-paid-network-debit', null, { reload: 'person-paid-network-debit' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
