(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('person-region-connection', {
            parent: 'entity',
            url: '/person-region-connection?page&sort&search&person_id={id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.personRegionConnection.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/person-region-connection/person-region-connections.html',
                    controller: 'PersonRegionConnectionController',
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
                    $translatePartialLoader.addPart('personRegionConnection');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('person-region-connection-detail', {
            parent: 'entity',
            url: '/person-region-connection/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.personRegionConnection.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/person-region-connection/person-region-connection-detail.html',
                    controller: 'PersonRegionConnectionDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('personRegionConnection');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PersonRegionConnection', function($stateParams, PersonRegionConnection) {
                    return PersonRegionConnection.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'person-region-connection',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('person-region-connection-detail.edit', {
            parent: 'person-region-connection-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-region-connection/person-region-connection-dialog.html',
                    controller: 'PersonRegionConnectionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PersonRegionConnection', function(PersonRegionConnection) {
                            return PersonRegionConnection.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('person-region-connection.new', {
            parent: 'person-region-connection',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-region-connection/person-region-connection-dialog.html',
                    controller: 'PersonRegionConnectionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                count: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('person-region-connection', null, { reload: 'person-region-connection' });
                }, function() {
                    $state.go('person-region-connection');
                });
            }]
        })
        .state('person-region-connection.edit', {
            parent: 'person-region-connection',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-region-connection/person-region-connection-dialog.html',
                    controller: 'PersonRegionConnectionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PersonRegionConnection', function(PersonRegionConnection) {
                            return PersonRegionConnection.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('person-region-connection', null, { reload: 'person-region-connection' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('person-region-connection.delete', {
            parent: 'person-region-connection',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-region-connection/person-region-connection-delete-dialog.html',
                    controller: 'PersonRegionConnectionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PersonRegionConnection', function(PersonRegionConnection) {
                            return PersonRegionConnection.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('person-region-connection', null, { reload: 'person-region-connection' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
