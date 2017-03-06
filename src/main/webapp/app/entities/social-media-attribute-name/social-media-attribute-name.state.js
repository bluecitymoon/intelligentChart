(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('social-media-attribute-name', {
            parent: 'entity',
            url: '/social-media-attribute-name?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.socialMediaAttributeName.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/social-media-attribute-name/social-media-attribute-names.html',
                    controller: 'SocialMediaAttributeNameController',
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
                    $translatePartialLoader.addPart('socialMediaAttributeName');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('social-media-attribute-name-detail', {
            parent: 'entity',
            url: '/social-media-attribute-name/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.socialMediaAttributeName.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/social-media-attribute-name/social-media-attribute-name-detail.html',
                    controller: 'SocialMediaAttributeNameDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('socialMediaAttributeName');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'SocialMediaAttributeName', function($stateParams, SocialMediaAttributeName) {
                    return SocialMediaAttributeName.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'social-media-attribute-name',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('social-media-attribute-name-detail.edit', {
            parent: 'social-media-attribute-name-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/social-media-attribute-name/social-media-attribute-name-dialog.html',
                    controller: 'SocialMediaAttributeNameDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SocialMediaAttributeName', function(SocialMediaAttributeName) {
                            return SocialMediaAttributeName.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('social-media-attribute-name.new', {
            parent: 'social-media-attribute-name',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/social-media-attribute-name/social-media-attribute-name-dialog.html',
                    controller: 'SocialMediaAttributeNameDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                identifier: null,
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('social-media-attribute-name', null, { reload: 'social-media-attribute-name' });
                }, function() {
                    $state.go('social-media-attribute-name');
                });
            }]
        })
        .state('social-media-attribute-name.edit', {
            parent: 'social-media-attribute-name',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/social-media-attribute-name/social-media-attribute-name-dialog.html',
                    controller: 'SocialMediaAttributeNameDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SocialMediaAttributeName', function(SocialMediaAttributeName) {
                            return SocialMediaAttributeName.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('social-media-attribute-name', null, { reload: 'social-media-attribute-name' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('social-media-attribute-name.delete', {
            parent: 'social-media-attribute-name',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/social-media-attribute-name/social-media-attribute-name-delete-dialog.html',
                    controller: 'SocialMediaAttributeNameDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['SocialMediaAttributeName', function(SocialMediaAttributeName) {
                            return SocialMediaAttributeName.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('social-media-attribute-name', null, { reload: 'social-media-attribute-name' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
