(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('double-movie-subject', {
            parent: 'entity',
            url: '/double-movie-subject?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.doubleMovieSubject.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/double-movie-subject/double-movie-subjects.html',
                    controller: 'DoubleMovieSubjectController',
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
                    $translatePartialLoader.addPart('doubleMovieSubject');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('double-movie-subject-detail', {
            parent: 'entity',
            url: '/double-movie-subject/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.doubleMovieSubject.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/double-movie-subject/double-movie-subject-detail.html',
                    controller: 'DoubleMovieSubjectDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('doubleMovieSubject');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'DoubleMovieSubject', function($stateParams, DoubleMovieSubject) {
                    return DoubleMovieSubject.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'double-movie-subject',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('double-movie-subject-detail.edit', {
            parent: 'double-movie-subject-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/double-movie-subject/double-movie-subject-dialog.html',
                    controller: 'DoubleMovieSubjectDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['DoubleMovieSubject', function(DoubleMovieSubject) {
                            return DoubleMovieSubject.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('double-movie-subject.new', {
            parent: 'double-movie-subject',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/double-movie-subject/double-movie-subject-dialog.html',
                    controller: 'DoubleMovieSubjectDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                title: null,
                                url: null,
                                cover: null,
                                rate: null,
                                doubanId: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('double-movie-subject', null, { reload: 'double-movie-subject' });
                }, function() {
                    $state.go('double-movie-subject');
                });
            }]
        })
        .state('double-movie-subject.edit', {
            parent: 'double-movie-subject',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/double-movie-subject/double-movie-subject-dialog.html',
                    controller: 'DoubleMovieSubjectDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['DoubleMovieSubject', function(DoubleMovieSubject) {
                            return DoubleMovieSubject.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('double-movie-subject', null, { reload: 'double-movie-subject' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('double-movie-subject.delete', {
            parent: 'double-movie-subject',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/double-movie-subject/double-movie-subject-delete-dialog.html',
                    controller: 'DoubleMovieSubjectDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['DoubleMovieSubject', function(DoubleMovieSubject) {
                            return DoubleMovieSubject.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('double-movie-subject', null, { reload: 'double-movie-subject' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
