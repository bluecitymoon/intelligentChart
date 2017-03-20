(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('person-tie-ba', {
            parent: 'entity',
            url: '/person-tie-ba?page&sort&search&person_id={id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.personTieBa.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/person-tie-ba/person-tie-bas.html',
                    controller: 'PersonTieBaController',
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
                    $translatePartialLoader.addPart('personTieBa');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('person-tie-ba-detail', {
            parent: 'entity',
            url: '/person-tie-ba/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.personTieBa.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/person-tie-ba/person-tie-ba-detail.html',
                    controller: 'PersonTieBaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('personTieBa');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PersonTieBa', function($stateParams, PersonTieBa) {
                    return PersonTieBa.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'person-tie-ba',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('person-tie-ba-detail.edit', {
            parent: 'person-tie-ba-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-tie-ba/person-tie-ba-dialog.html',
                    controller: 'PersonTieBaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PersonTieBa', function(PersonTieBa) {
                            return PersonTieBa.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('person-tie-ba.new', {
            parent: 'person-tie-ba',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-tie-ba/person-tie-ba-dialog.html',
                    controller: 'PersonTieBaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                userCount: null,
                                postCount: null,
                                shortDescription: null,
                                rankDescription: null,
                                rank: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('person-tie-ba', null, { reload: 'person-tie-ba' });
                }, function() {
                    $state.go('person-tie-ba');
                });
            }]
        })
        .state('person-tie-ba.edit', {
            parent: 'person-tie-ba',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-tie-ba/person-tie-ba-dialog.html',
                    controller: 'PersonTieBaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PersonTieBa', function(PersonTieBa) {
                            return PersonTieBa.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('person-tie-ba', null, { reload: 'person-tie-ba' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('person-tie-ba.delete', {
            parent: 'person-tie-ba',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-tie-ba/person-tie-ba-delete-dialog.html',
                    controller: 'PersonTieBaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PersonTieBa', function(PersonTieBa) {
                            return PersonTieBa.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('person-tie-ba', null, { reload: 'person-tie-ba' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
