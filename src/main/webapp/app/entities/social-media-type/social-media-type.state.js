(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('social-media-type', {
            parent: 'entity',
            url: '/social-media-type?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.socialMediaType.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/social-media-type/social-media-types.html',
                    controller: 'SocialMediaTypeController',
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
                    $translatePartialLoader.addPart('socialMediaType');
                    $translatePartialLoader.addPart('panelStyle');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('social-media-type-detail', {
            parent: 'entity',
            url: '/social-media-type/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.socialMediaType.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/social-media-type/social-media-type-detail.html',
                    controller: 'SocialMediaTypeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('socialMediaType');
                    $translatePartialLoader.addPart('panelStyle');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'SocialMediaType', function($stateParams, SocialMediaType) {
                    return SocialMediaType.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'social-media-type',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('social-media-type-detail.edit', {
            parent: 'social-media-type-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/social-media-type/social-media-type-dialog.html',
                    controller: 'SocialMediaTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SocialMediaType', function(SocialMediaType) {
                            return SocialMediaType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('social-media-type.new', {
            parent: 'social-media-type',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/social-media-type/social-media-type-dialog.html',
                    controller: 'SocialMediaTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                identifier: null,
                                name: null,
                                style: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('social-media-type', null, { reload: 'social-media-type' });
                }, function() {
                    $state.go('social-media-type');
                });
            }]
        })
        .state('social-media-type.edit', {
            parent: 'social-media-type',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/social-media-type/social-media-type-dialog.html',
                    controller: 'SocialMediaTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SocialMediaType', function(SocialMediaType) {
                            return SocialMediaType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('social-media-type', null, { reload: 'social-media-type' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('social-media-type.delete', {
            parent: 'social-media-type',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/social-media-type/social-media-type-delete-dialog.html',
                    controller: 'SocialMediaTypeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['SocialMediaType', function(SocialMediaType) {
                            return SocialMediaType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('social-media-type', null, { reload: 'social-media-type' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
