(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('InnovationTypeDialogController', InnovationTypeDialogController);

    InnovationTypeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'InnovationType'];

    function InnovationTypeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, InnovationType) {
        var vm = this;

        vm.innovationType = entity;
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
            if (vm.innovationType.id !== null) {
                InnovationType.update(vm.innovationType, onSaveSuccess, onSaveError);
            } else {
                InnovationType.save(vm.innovationType, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('intelligentChartApp:innovationTypeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
