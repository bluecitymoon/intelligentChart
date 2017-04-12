(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('douban-movie-tag', {
            parent: 'entity',
            url: '/douban-movie-tag?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.doubanMovieTag.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/douban-movie-tag/douban-movie-tags.html',
                    controller: 'DoubanMovieTagController',
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
                    $translatePartialLoader.addPart('doubanMovieTag');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('douban-movie-tag-detail', {
            parent: 'entity',
            url: '/douban-movie-tag/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.doubanMovieTag.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/douban-movie-tag/douban-movie-tag-detail.html',
                    controller: 'DoubanMovieTagDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('doubanMovieTag');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'DoubanMovieTag', function($stateParams, DoubanMovieTag) {
                    return DoubanMovieTag.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'douban-movie-tag',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('douban-movie-tag-detail.edit', {
            parent: 'douban-movie-tag-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/douban-movie-tag/douban-movie-tag-dialog.html',
                    controller: 'DoubanMovieTagDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['DoubanMovieTag', function(DoubanMovieTag) {
                            return DoubanMovieTag.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('douban-movie-tag.new', {
            parent: 'douban-movie-tag',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/douban-movie-tag/douban-movie-tag-dialog.html',
                    controller: 'DoubanMovieTagDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('douban-movie-tag', null, { reload: 'douban-movie-tag' });
                }, function() {
                    $state.go('douban-movie-tag');
                });
            }]
        })
        .state('douban-movie-tag.edit', {
            parent: 'douban-movie-tag',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/douban-movie-tag/douban-movie-tag-dialog.html',
                    controller: 'DoubanMovieTagDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['DoubanMovieTag', function(DoubanMovieTag) {
                            return DoubanMovieTag.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('douban-movie-tag', null, { reload: 'douban-movie-tag' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('douban-movie-tag.delete', {
            parent: 'douban-movie-tag',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/douban-movie-tag/douban-movie-tag-delete-dialog.html',
                    controller: 'DoubanMovieTagDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['DoubanMovieTag', function(DoubanMovieTag) {
                            return DoubanMovieTag.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('douban-movie-tag', null, { reload: 'douban-movie-tag' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
