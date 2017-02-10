(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('person-connection-level', {
            parent: 'entity',
            url: '/person-connection-level?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.personConnectionLevel.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/person-connection-level/person-connection-levels.html',
                    controller: 'PersonConnectionLevelController',
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
                    $translatePartialLoader.addPart('personConnectionLevel');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('person-connection-level-detail', {
            parent: 'entity',
            url: '/person-connection-level/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.personConnectionLevel.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/person-connection-level/person-connection-level-detail.html',
                    controller: 'PersonConnectionLevelDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('personConnectionLevel');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PersonConnectionLevel', function($stateParams, PersonConnectionLevel) {
                    return PersonConnectionLevel.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'person-connection-level',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('person-connection-level-detail.edit', {
            parent: 'person-connection-level-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-connection-level/person-connection-level-dialog.html',
                    controller: 'PersonConnectionLevelDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PersonConnectionLevel', function(PersonConnectionLevel) {
                            return PersonConnectionLevel.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('person-connection-level.new', {
            parent: 'person-connection-level',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-connection-level/person-connection-level-dialog.html',
                    controller: 'PersonConnectionLevelDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                percentage: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('person-connection-level', null, { reload: 'person-connection-level' });
                }, function() {
                    $state.go('person-connection-level');
                });
            }]
        })
        .state('person-connection-level.edit', {
            parent: 'person-connection-level',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-connection-level/person-connection-level-dialog.html',
                    controller: 'PersonConnectionLevelDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PersonConnectionLevel', function(PersonConnectionLevel) {
                            return PersonConnectionLevel.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('person-connection-level', null, { reload: 'person-connection-level' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('person-connection-level.delete', {
            parent: 'person-connection-level',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-connection-level/person-connection-level-delete-dialog.html',
                    controller: 'PersonConnectionLevelDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PersonConnectionLevel', function(PersonConnectionLevel) {
                            return PersonConnectionLevel.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('person-connection-level', null, { reload: 'person-connection-level' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
