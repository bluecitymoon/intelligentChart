(function() {
    'use strict';
    angular
        .module('intelligentChartApp')
        .factory('LawBusinessType', LawBusinessType);

    LawBusinessType.$inject = ['$resource'];

    function LawBusinessType ($resource) {
        var resourceUrl =  'api/law-business-types/:id';

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
