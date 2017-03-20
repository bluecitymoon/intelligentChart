(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('person-network-shopping', {
            parent: 'entity',
            url: '/person-network-shopping?page&sort&search&person_id={id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.personNetworkShopping.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/person-network-shopping/person-network-shoppings.html',
                    controller: 'PersonNetworkShoppingController',
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
                    $translatePartialLoader.addPart('personNetworkShopping');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('person-network-shopping-detail', {
            parent: 'entity',
            url: '/person-network-shopping/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.personNetworkShopping.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/person-network-shopping/person-network-shopping-detail.html',
                    controller: 'PersonNetworkShoppingDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('personNetworkShopping');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PersonNetworkShopping', function($stateParams, PersonNetworkShopping) {
                    return PersonNetworkShopping.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'person-network-shopping',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('person-network-shopping-detail.edit', {
            parent: 'person-network-shopping-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-network-shopping/person-network-shopping-dialog.html',
                    controller: 'PersonNetworkShoppingDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PersonNetworkShopping', function(PersonNetworkShopping) {
                            return PersonNetworkShopping.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('person-network-shopping.new', {
            parent: 'person-network-shopping',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-network-shopping/person-network-shopping-dialog.html',
                    controller: 'PersonNetworkShoppingDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                createDate: null,
                                amount: null,
                                description: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('person-network-shopping', null, { reload: 'person-network-shopping' });
                }, function() {
                    $state.go('person-network-shopping');
                });
            }]
        })
        .state('person-network-shopping.edit', {
            parent: 'person-network-shopping',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-network-shopping/person-network-shopping-dialog.html',
                    controller: 'PersonNetworkShoppingDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PersonNetworkShopping', function(PersonNetworkShopping) {
                            return PersonNetworkShopping.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('person-network-shopping', null, { reload: 'person-network-shopping' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('person-network-shopping.delete', {
            parent: 'person-network-shopping',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-network-shopping/person-network-shopping-delete-dialog.html',
                    controller: 'PersonNetworkShoppingDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PersonNetworkShopping', function(PersonNetworkShopping) {
                            return PersonNetworkShopping.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('person-network-shopping', null, { reload: 'person-network-shopping' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
