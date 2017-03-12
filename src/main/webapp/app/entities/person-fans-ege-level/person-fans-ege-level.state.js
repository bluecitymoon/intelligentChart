(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('person-fans-ege-level', {
            parent: 'entity',
            url: '/person-fans-ege-level?page&sort&search&person_id={id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.personFansEgeLevel.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/person-fans-ege-level/person-fans-ege-levels.html',
                    controller: 'PersonFansEgeLevelController',
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
                    $translatePartialLoader.addPart('personFansEgeLevel');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('person-fans-ege-level-detail', {
            parent: 'entity',
            url: '/person-fans-ege-level/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.personFansEgeLevel.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/person-fans-ege-level/person-fans-ege-level-detail.html',
                    controller: 'PersonFansEgeLevelDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('personFansEgeLevel');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PersonFansEgeLevel', function($stateParams, PersonFansEgeLevel) {
                    return PersonFansEgeLevel.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'person-fans-ege-level',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('person-fans-ege-level-detail.edit', {
            parent: 'person-fans-ege-level-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-fans-ege-level/person-fans-ege-level-dialog.html',
                    controller: 'PersonFansEgeLevelDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PersonFansEgeLevel', function(PersonFansEgeLevel) {
                            return PersonFansEgeLevel.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('person-fans-ege-level.new', {
            parent: 'person-fans-ege-level',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-fans-ege-level/person-fans-ege-level-dialog.html',
                    controller: 'PersonFansEgeLevelDialogController',
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
                    $state.go('person-fans-ege-level', null, { reload: 'person-fans-ege-level' });
                }, function() {
                    $state.go('person-fans-ege-level');
                });
            }]
        })
        .state('person-fans-ege-level.edit', {
            parent: 'person-fans-ege-level',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-fans-ege-level/person-fans-ege-level-dialog.html',
                    controller: 'PersonFansEgeLevelDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PersonFansEgeLevel', function(PersonFansEgeLevel) {
                            return PersonFansEgeLevel.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('person-fans-ege-level', null, { reload: 'person-fans-ege-level' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('person-fans-ege-level.delete', {
            parent: 'person-fans-ege-level',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-fans-ege-level/person-fans-ege-level-delete-dialog.html',
                    controller: 'PersonFansEgeLevelDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PersonFansEgeLevel', function(PersonFansEgeLevel) {
                            return PersonFansEgeLevel.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('person-fans-ege-level', null, { reload: 'person-fans-ege-level' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
