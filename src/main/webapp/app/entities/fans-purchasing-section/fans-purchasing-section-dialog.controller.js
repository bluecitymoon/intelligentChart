(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('FansPurchasingSectionDialogController', FansPurchasingSectionDialogController);

    FansPurchasingSectionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'FansPurchasingSection'];

    function FansPurchasingSectionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, FansPurchasingSection) {
        var vm = this;

        vm.fansPurchasingSection = entity;
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
            if (vm.fansPurchasingSection.id !== null) {
                FansPurchasingSection.update(vm.fansPurchasingSection, onSaveSuccess, onSaveError);
            } else {
                FansPurchasingSection.save(vm.fansPurchasingSection, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('intelligentChartApp:fansPurchasingSectionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
