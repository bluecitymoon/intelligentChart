(function() {
    'use strict';
    angular
        .module('intelligentChartApp')
        .factory('Menu', Menu);

    Menu.$inject = ['$resource'];

    function Menu ($resource) {
        var resourceUrl =  'api/menus/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
