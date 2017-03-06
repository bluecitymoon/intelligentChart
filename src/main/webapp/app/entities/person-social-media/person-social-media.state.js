(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('person-social-media', {
            parent: 'entity',
            url: '/person-social-media?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.personSocialMedia.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/person-social-media/person-social-medias.html',
                    controller: 'PersonSocialMediaController',
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
                    $translatePartialLoader.addPart('personSocialMedia');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('person-social-media-detail', {
            parent: 'entity',
            url: '/person-social-media/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.personSocialMedia.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/person-social-media/person-social-media-detail.html',
                    controller: 'PersonSocialMediaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('personSocialMedia');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PersonSocialMedia', function($stateParams, PersonSocialMedia) {
                    return PersonSocialMedia.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'person-social-media',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('person-social-media-detail.edit', {
            parent: 'person-social-media-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-social-media/person-social-media-dialog.html',
                    controller: 'PersonSocialMediaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PersonSocialMedia', function(PersonSocialMedia) {
                            return PersonSocialMedia.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('person-social-media.new', {
            parent: 'person-social-media',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-social-media/person-social-media-dialog.html',
                    controller: 'PersonSocialMediaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                attributeValue: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('person-social-media', null, { reload: 'person-social-media' });
                }, function() {
                    $state.go('person-social-media');
                });
            }]
        })
        .state('person-social-media.edit', {
            parent: 'person-social-media',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-social-media/person-social-media-dialog.html',
                    controller: 'PersonSocialMediaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PersonSocialMedia', function(PersonSocialMedia) {
                            return PersonSocialMedia.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('person-social-media', null, { reload: 'person-social-media' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('person-social-media.delete', {
            parent: 'person-social-media',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-social-media/person-social-media-delete-dialog.html',
                    controller: 'PersonSocialMediaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PersonSocialMedia', function(PersonSocialMedia) {
                            return PersonSocialMedia.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('person-social-media', null, { reload: 'person-social-media' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
