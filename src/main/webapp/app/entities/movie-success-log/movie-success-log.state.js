(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('movie-success-log', {
            parent: 'entity',
            url: '/movie-success-log?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.movieSuccessLog.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/movie-success-log/movie-success-logs.html',
                    controller: 'MovieSuccessLogController',
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
                    $translatePartialLoader.addPart('movieSuccessLog');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('movie-success-log-detail', {
            parent: 'entity',
            url: '/movie-success-log/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.movieSuccessLog.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/movie-success-log/movie-success-log-detail.html',
                    controller: 'MovieSuccessLogDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('movieSuccessLog');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'MovieSuccessLog', function($stateParams, MovieSuccessLog) {
                    return MovieSuccessLog.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'movie-success-log',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('movie-success-log-detail.edit', {
            parent: 'movie-success-log-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/movie-success-log/movie-success-log-dialog.html',
                    controller: 'MovieSuccessLogDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['MovieSuccessLog', function(MovieSuccessLog) {
                            return MovieSuccessLog.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('movie-success-log.new', {
            parent: 'movie-success-log',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/movie-success-log/movie-success-log-dialog.html',
                    controller: 'MovieSuccessLogDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                movieId: null,
                                name: null,
                                doubanId: null,
                                createDate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('movie-success-log', null, { reload: 'movie-success-log' });
                }, function() {
                    $state.go('movie-success-log');
                });
            }]
        })
        .state('movie-success-log.edit', {
            parent: 'movie-success-log',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/movie-success-log/movie-success-log-dialog.html',
                    controller: 'MovieSuccessLogDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['MovieSuccessLog', function(MovieSuccessLog) {
                            return MovieSuccessLog.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('movie-success-log', null, { reload: 'movie-success-log' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('movie-success-log.delete', {
            parent: 'movie-success-log',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/movie-success-log/movie-success-log-delete-dialog.html',
                    controller: 'MovieSuccessLogDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['MovieSuccessLog', function(MovieSuccessLog) {
                            return MovieSuccessLog.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('movie-success-log', null, { reload: 'movie-success-log' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
