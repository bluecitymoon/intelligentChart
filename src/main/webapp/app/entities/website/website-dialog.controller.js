(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('WebsiteDialogController', WebsiteDialogController);

    WebsiteDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Website'];

    function WebsiteDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Website) {
        var vm = this;

        vm.website = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.website.id !== null) {
                Website.update(vm.website, onSaveSuccess, onSaveError);
            } else {
                Website.save(vm.website, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('intelligentChartApp:websiteUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
