(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonWechatArticleDetailController', PersonWechatArticleDetailController);

    PersonWechatArticleDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PersonWechatArticle', 'Person'];

    function PersonWechatArticleDetailController($scope, $rootScope, $stateParams, previousState, entity, PersonWechatArticle, Person) {
        var vm = this;

        vm.personWechatArticle = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('intelligentChartApp:personWechatArticleUpdate', function(event, result) {
            vm.personWechatArticle = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
