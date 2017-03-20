(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('person-wechat-article', {
            parent: 'entity',
            url: '/person-wechat-article?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.personWechatArticle.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/person-wechat-article/person-wechat-articles.html',
                    controller: 'PersonWechatArticleController',
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
                    $translatePartialLoader.addPart('personWechatArticle');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('person-wechat-article-detail', {
            parent: 'entity',
            url: '/person-wechat-article/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.personWechatArticle.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/person-wechat-article/person-wechat-article-detail.html',
                    controller: 'PersonWechatArticleDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('personWechatArticle');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PersonWechatArticle', function($stateParams, PersonWechatArticle) {
                    return PersonWechatArticle.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'person-wechat-article',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('person-wechat-article-detail.edit', {
            parent: 'person-wechat-article-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-wechat-article/person-wechat-article-dialog.html',
                    controller: 'PersonWechatArticleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PersonWechatArticle', function(PersonWechatArticle) {
                            return PersonWechatArticle.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('person-wechat-article.new', {
            parent: 'person-wechat-article',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-wechat-article/person-wechat-article-dialog.html',
                    controller: 'PersonWechatArticleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                count: null,
                                increadByLastMonth: null,
                                createDate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('person-wechat-article', null, { reload: 'person-wechat-article' });
                }, function() {
                    $state.go('person-wechat-article');
                });
            }]
        })
        .state('person-wechat-article.edit', {
            parent: 'person-wechat-article',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-wechat-article/person-wechat-article-dialog.html',
                    controller: 'PersonWechatArticleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PersonWechatArticle', function(PersonWechatArticle) {
                            return PersonWechatArticle.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('person-wechat-article', null, { reload: 'person-wechat-article' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('person-wechat-article.delete', {
            parent: 'person-wechat-article',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-wechat-article/person-wechat-article-delete-dialog.html',
                    controller: 'PersonWechatArticleDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PersonWechatArticle', function(PersonWechatArticle) {
                            return PersonWechatArticle.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('person-wechat-article', null, { reload: 'person-wechat-article' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
