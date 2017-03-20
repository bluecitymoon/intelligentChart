(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('person-fans-hobby', {
            parent: 'entity',
            url: '/person-fans-hobby?page&sort&search&person_id={id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.personFansHobby.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/person-fans-hobby/person-fans-hobbies.html',
                    controller: 'PersonFansHobbyController',
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
                    $translatePartialLoader.addPart('personFansHobby');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('person-fans-hobby-detail', {
            parent: 'entity',
            url: '/person-fans-hobby/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.personFansHobby.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/person-fans-hobby/person-fans-hobby-detail.html',
                    controller: 'PersonFansHobbyDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('personFansHobby');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PersonFansHobby', function($stateParams, PersonFansHobby) {
                    return PersonFansHobby.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'person-fans-hobby',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('person-fans-hobby-detail.edit', {
            parent: 'person-fans-hobby-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-fans-hobby/person-fans-hobby-dialog.html',
                    controller: 'PersonFansHobbyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PersonFansHobby', function(PersonFansHobby) {
                            return PersonFansHobby.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('person-fans-hobby.new', {
            parent: 'person-fans-hobby',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-fans-hobby/person-fans-hobby-dialog.html',
                    controller: 'PersonFansHobbyDialogController',
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
                    $state.go('person-fans-hobby', null, { reload: 'person-fans-hobby' });
                }, function() {
                    $state.go('person-fans-hobby');
                });
            }]
        })
        .state('person-fans-hobby.edit', {
            parent: 'person-fans-hobby',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-fans-hobby/person-fans-hobby-dialog.html',
                    controller: 'PersonFansHobbyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PersonFansHobby', function(PersonFansHobby) {
                            return PersonFansHobby.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('person-fans-hobby', null, { reload: 'person-fans-hobby' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('person-fans-hobby.delete', {
            parent: 'person-fans-hobby',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-fans-hobby/person-fans-hobby-delete-dialog.html',
                    controller: 'PersonFansHobbyDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PersonFansHobby', function(PersonFansHobby) {
                            return PersonFansHobby.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('person-fans-hobby', null, { reload: 'person-fans-hobby' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
