(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('robot-movie-subject-success-page', {
            parent: 'entity',
            url: '/robot-movie-subject-success-page?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.robotMovieSubjectSuccessPage.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/robot-movie-subject-success-page/robot-movie-subject-success-pages.html',
                    controller: 'RobotMovieSubjectSuccessPageController',
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
                    $translatePartialLoader.addPart('robotMovieSubjectSuccessPage');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('robot-movie-subject-success-page-detail', {
            parent: 'entity',
            url: '/robot-movie-subject-success-page/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.robotMovieSubjectSuccessPage.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/robot-movie-subject-success-page/robot-movie-subject-success-page-detail.html',
                    controller: 'RobotMovieSubjectSuccessPageDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('robotMovieSubjectSuccessPage');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'RobotMovieSubjectSuccessPage', function($stateParams, RobotMovieSubjectSuccessPage) {
                    return RobotMovieSubjectSuccessPage.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'robot-movie-subject-success-page',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('robot-movie-subject-success-page-detail.edit', {
            parent: 'robot-movie-subject-success-page-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/robot-movie-subject-success-page/robot-movie-subject-success-page-dialog.html',
                    controller: 'RobotMovieSubjectSuccessPageDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['RobotMovieSubjectSuccessPage', function(RobotMovieSubjectSuccessPage) {
                            return RobotMovieSubjectSuccessPage.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('robot-movie-subject-success-page.new', {
            parent: 'robot-movie-subject-success-page',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/robot-movie-subject-success-page/robot-movie-subject-success-page-dialog.html',
                    controller: 'RobotMovieSubjectSuccessPageDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                tag: null,
                                pageNumber: null,
                                createDate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('robot-movie-subject-success-page', null, { reload: 'robot-movie-subject-success-page' });
                }, function() {
                    $state.go('robot-movie-subject-success-page');
                });
            }]
        })
        .state('robot-movie-subject-success-page.edit', {
            parent: 'robot-movie-subject-success-page',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/robot-movie-subject-success-page/robot-movie-subject-success-page-dialog.html',
                    controller: 'RobotMovieSubjectSuccessPageDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['RobotMovieSubjectSuccessPage', function(RobotMovieSubjectSuccessPage) {
                            return RobotMovieSubjectSuccessPage.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('robot-movie-subject-success-page', null, { reload: 'robot-movie-subject-success-page' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('robot-movie-subject-success-page.delete', {
            parent: 'robot-movie-subject-success-page',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/robot-movie-subject-success-page/robot-movie-subject-success-page-delete-dialog.html',
                    controller: 'RobotMovieSubjectSuccessPageDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['RobotMovieSubjectSuccessPage', function(RobotMovieSubjectSuccessPage) {
                            return RobotMovieSubjectSuccessPage.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('robot-movie-subject-success-page', null, { reload: 'robot-movie-subject-success-page' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
