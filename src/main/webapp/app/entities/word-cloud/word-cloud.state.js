(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('word-cloud', {
            parent: 'entity',
            url: '/word-cloud?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.wordCloud.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/word-cloud/word-clouds.html',
                    controller: 'WordCloudController',
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
                    $translatePartialLoader.addPart('wordCloud');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('word-cloud-detail', {
            parent: 'entity',
            url: '/word-cloud/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.wordCloud.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/word-cloud/word-cloud-detail.html',
                    controller: 'WordCloudDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('wordCloud');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'WordCloud', function($stateParams, WordCloud) {
                    return WordCloud.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'word-cloud',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('word-cloud-detail.edit', {
            parent: 'word-cloud-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/word-cloud/word-cloud-dialog.html',
                    controller: 'WordCloudDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['WordCloud', function(WordCloud) {
                            return WordCloud.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('word-cloud.new', {
            parent: 'word-cloud',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/word-cloud/word-cloud-dialog.html',
                    controller: 'WordCloudDialogController',
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
                    $state.go('word-cloud', null, { reload: 'word-cloud' });
                }, function() {
                    $state.go('word-cloud');
                });
            }]
        })
        .state('word-cloud.edit', {
            parent: 'word-cloud',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/word-cloud/word-cloud-dialog.html',
                    controller: 'WordCloudDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['WordCloud', function(WordCloud) {
                            return WordCloud.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('word-cloud', null, { reload: 'word-cloud' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('word-cloud.delete', {
            parent: 'word-cloud',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/word-cloud/word-cloud-delete-dialog.html',
                    controller: 'WordCloudDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['WordCloud', function(WordCloud) {
                            return WordCloud.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('word-cloud', null, { reload: 'word-cloud' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
