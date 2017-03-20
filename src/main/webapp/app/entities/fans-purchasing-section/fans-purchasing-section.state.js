(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('fans-purchasing-section', {
            parent: 'entity',
            url: '/fans-purchasing-section&person_id={id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.fansPurchasingSection.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/fans-purchasing-section/fans-purchasing-sections.html',
                    controller: 'FansPurchasingSectionController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('fansPurchasingSection');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('fans-purchasing-section-detail', {
            parent: 'entity',
            url: '/fans-purchasing-section/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intelligentChartApp.fansPurchasingSection.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/fans-purchasing-section/fans-purchasing-section-detail.html',
                    controller: 'FansPurchasingSectionDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('fansPurchasingSection');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'FansPurchasingSection', function($stateParams, FansPurchasingSection) {
                    return FansPurchasingSection.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'fans-purchasing-section',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('fans-purchasing-section-detail.edit', {
            parent: 'fans-purchasing-section-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/fans-purchasing-section/fans-purchasing-section-dialog.html',
                    controller: 'FansPurchasingSectionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['FansPurchasingSection', function(FansPurchasingSection) {
                            return FansPurchasingSection.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('fans-purchasing-section.new', {
            parent: 'fans-purchasing-section',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/fans-purchasing-section/fans-purchasing-section-dialog.html',
                    controller: 'FansPurchasingSectionDialogController',
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
                    $state.go('fans-purchasing-section', null, { reload: 'fans-purchasing-section' });
                }, function() {
                    $state.go('fans-purchasing-section');
                });
            }]
        })
        .state('fans-purchasing-section.edit', {
            parent: 'fans-purchasing-section',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/fans-purchasing-section/fans-purchasing-section-dialog.html',
                    controller: 'FansPurchasingSectionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['FansPurchasingSection', function(FansPurchasingSection) {
                            return FansPurchasingSection.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('fans-purchasing-section', null, { reload: 'fans-purchasing-section' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('fans-purchasing-section.delete', {
            parent: 'fans-purchasing-section',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/fans-purchasing-section/fans-purchasing-section-delete-dialog.html',
                    controller: 'FansPurchasingSectionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['FansPurchasingSection', function(FansPurchasingSection) {
                            return FansPurchasingSection.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('fans-purchasing-section', null, { reload: 'fans-purchasing-section' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
