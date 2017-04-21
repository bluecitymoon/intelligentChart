(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('movie-participant', {
            parent: 'entity',
            url: '/movie-participant?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.movieParticipant.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/movie-participant/movie-participants.html',
                    controller: 'MovieParticipantController',
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
                    $translatePartialLoader.addPart('movieParticipant');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('movie-participant-detail', {
            parent: 'entity',
            url: '/movie-participant/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.movieParticipant.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/movie-participant/movie-participant-detail.html',
                    controller: 'MovieParticipantDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('movieParticipant');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'MovieParticipant', function($stateParams, MovieParticipant) {
                    return MovieParticipant.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'movie-participant',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('movie-participant-detail.edit', {
            parent: 'movie-participant-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/movie-participant/movie-participant-dialog.html',
                    controller: 'MovieParticipantDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['MovieParticipant', function(MovieParticipant) {
                            return MovieParticipant.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('movie-participant.new', {
            parent: 'movie-participant',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/movie-participant/movie-participant-dialog.html',
                    controller: 'MovieParticipantDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                createDate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('movie-participant', null, { reload: 'movie-participant' });
                }, function() {
                    $state.go('movie-participant');
                });
            }]
        })
        .state('movie-participant.edit', {
            parent: 'movie-participant',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/movie-participant/movie-participant-dialog.html',
                    controller: 'MovieParticipantDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['MovieParticipant', function(MovieParticipant) {
                            return MovieParticipant.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('movie-participant', null, { reload: 'movie-participant' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('movie-participant.delete', {
            parent: 'movie-participant',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/movie-participant/movie-participant-delete-dialog.html',
                    controller: 'MovieParticipantDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['MovieParticipant', function(MovieParticipant) {
                            return MovieParticipant.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('movie-participant', null, { reload: 'movie-participant' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
