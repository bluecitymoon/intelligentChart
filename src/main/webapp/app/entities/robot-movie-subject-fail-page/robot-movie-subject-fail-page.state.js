(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('robot-movie-subject-fail-page', {
            parent: 'entity',
            url: '/robot-movie-subject-fail-page?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.robotMovieSubjectFailPage.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/robot-movie-subject-fail-page/robot-movie-subject-fail-pages.html',
                    controller: 'RobotMovieSubjectFailPageController',
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
                    $translatePartialLoader.addPart('robotMovieSubjectFailPage');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('robot-movie-subject-fail-page-detail', {
            parent: 'entity',
            url: '/robot-movie-subject-fail-page/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.robotMovieSubjectFailPage.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/robot-movie-subject-fail-page/robot-movie-subject-fail-page-detail.html',
                    controller: 'RobotMovieSubjectFailPageDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('robotMovieSubjectFailPage');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'RobotMovieSubjectFailPage', function($stateParams, RobotMovieSubjectFailPage) {
                    return RobotMovieSubjectFailPage.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'robot-movie-subject-fail-page',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('robot-movie-subject-fail-page-detail.edit', {
            parent: 'robot-movie-subject-fail-page-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/robot-movie-subject-fail-page/robot-movie-subject-fail-page-dialog.html',
                    controller: 'RobotMovieSubjectFailPageDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['RobotMovieSubjectFailPage', function(RobotMovieSubjectFailPage) {
                            return RobotMovieSubjectFailPage.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('robot-movie-subject-fail-page.new', {
            parent: 'robot-movie-subject-fail-page',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/robot-movie-subject-fail-page/robot-movie-subject-fail-page-dialog.html',
                    controller: 'RobotMovieSubjectFailPageDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                tag: null,
                                pageNumber: null,
                                createDate: null,
                                reason: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('robot-movie-subject-fail-page', null, { reload: 'robot-movie-subject-fail-page' });
                }, function() {
                    $state.go('robot-movie-subject-fail-page');
                });
            }]
        })
        .state('robot-movie-subject-fail-page.edit', {
            parent: 'robot-movie-subject-fail-page',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/robot-movie-subject-fail-page/robot-movie-subject-fail-page-dialog.html',
                    controller: 'RobotMovieSubjectFailPageDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['RobotMovieSubjectFailPage', function(RobotMovieSubjectFailPage) {
                            return RobotMovieSubjectFailPage.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('robot-movie-subject-fail-page', null, { reload: 'robot-movie-subject-fail-page' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('robot-movie-subject-fail-page.delete', {
            parent: 'robot-movie-subject-fail-page',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/robot-movie-subject-fail-page/robot-movie-subject-fail-page-delete-dialog.html',
                    controller: 'RobotMovieSubjectFailPageDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['RobotMovieSubjectFailPage', function(RobotMovieSubjectFailPage) {
                            return RobotMovieSubjectFailPage.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('robot-movie-subject-fail-page', null, { reload: 'robot-movie-subject-fail-page' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
