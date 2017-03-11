(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('person-social-hot-discuss', {
            parent: 'entity',
            url: '/person-social-hot-discuss?page&sort&search&person_id={id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.personSocialHotDiscuss.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/person-social-hot-discuss/person-social-hot-discusses.html',
                    controller: 'PersonSocialHotDiscussController',
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
                    $translatePartialLoader.addPart('personSocialHotDiscuss');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('person-social-hot-discuss-detail', {
            parent: 'entity',
            url: '/person-social-hot-discuss/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.personSocialHotDiscuss.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/person-social-hot-discuss/person-social-hot-discuss-detail.html',
                    controller: 'PersonSocialHotDiscussDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('personSocialHotDiscuss');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PersonSocialHotDiscuss', function($stateParams, PersonSocialHotDiscuss) {
                    return PersonSocialHotDiscuss.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'person-social-hot-discuss',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('person-social-hot-discuss-detail.edit', {
            parent: 'person-social-hot-discuss-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-social-hot-discuss/person-social-hot-discuss-dialog.html',
                    controller: 'PersonSocialHotDiscussDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PersonSocialHotDiscuss', function(PersonSocialHotDiscuss) {
                            return PersonSocialHotDiscuss.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('person-social-hot-discuss.new', {
            parent: 'person-social-hot-discuss',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-social-hot-discuss/person-social-hot-discuss-dialog.html',
                    controller: 'PersonSocialHotDiscussDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                articleTitle: null,
                                mediaName: null,
                                createDate: null,
                                articleCount: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('person-social-hot-discuss', null, { reload: 'person-social-hot-discuss' });
                }, function() {
                    $state.go('person-social-hot-discuss');
                });
            }]
        })
        .state('person-social-hot-discuss.edit', {
            parent: 'person-social-hot-discuss',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-social-hot-discuss/person-social-hot-discuss-dialog.html',
                    controller: 'PersonSocialHotDiscussDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PersonSocialHotDiscuss', function(PersonSocialHotDiscuss) {
                            return PersonSocialHotDiscuss.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('person-social-hot-discuss', null, { reload: 'person-social-hot-discuss' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('person-social-hot-discuss.delete', {
            parent: 'person-social-hot-discuss',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-social-hot-discuss/person-social-hot-discuss-delete-dialog.html',
                    controller: 'PersonSocialHotDiscussDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PersonSocialHotDiscuss', function(PersonSocialHotDiscuss) {
                            return PersonSocialHotDiscuss.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('person-social-hot-discuss', null, { reload: 'person-social-hot-discuss' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
