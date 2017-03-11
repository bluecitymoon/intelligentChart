(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('person-fans-education-level', {
            parent: 'entity',
            url: '/person-fans-education-level?page&sort&search&person_id={id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.personFansEducationLevel.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/person-fans-education-level/person-fans-education-levels.html',
                    controller: 'PersonFansEducationLevelController',
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
                    $translatePartialLoader.addPart('personFansEducationLevel');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('person-fans-education-level-detail', {
            parent: 'entity',
            url: '/person-fans-education-level/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.personFansEducationLevel.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/person-fans-education-level/person-fans-education-level-detail.html',
                    controller: 'PersonFansEducationLevelDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('personFansEducationLevel');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PersonFansEducationLevel', function($stateParams, PersonFansEducationLevel) {
                    return PersonFansEducationLevel.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'person-fans-education-level',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('person-fans-education-level-detail.edit', {
            parent: 'person-fans-education-level-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-fans-education-level/person-fans-education-level-dialog.html',
                    controller: 'PersonFansEducationLevelDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PersonFansEducationLevel', function(PersonFansEducationLevel) {
                            return PersonFansEducationLevel.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('person-fans-education-level.new', {
            parent: 'person-fans-education-level',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-fans-education-level/person-fans-education-level-dialog.html',
                    controller: 'PersonFansEducationLevelDialogController',
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
                    $state.go('person-fans-education-level', null, { reload: 'person-fans-education-level' });
                }, function() {
                    $state.go('person-fans-education-level');
                });
            }]
        })
        .state('person-fans-education-level.edit', {
            parent: 'person-fans-education-level',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-fans-education-level/person-fans-education-level-dialog.html',
                    controller: 'PersonFansEducationLevelDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PersonFansEducationLevel', function(PersonFansEducationLevel) {
                            return PersonFansEducationLevel.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('person-fans-education-level', null, { reload: 'person-fans-education-level' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('person-fans-education-level.delete', {
            parent: 'person-fans-education-level',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-fans-education-level/person-fans-education-level-delete-dialog.html',
                    controller: 'PersonFansEducationLevelDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PersonFansEducationLevel', function(PersonFansEducationLevel) {
                            return PersonFansEducationLevel.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('person-fans-education-level', null, { reload: 'person-fans-education-level' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
